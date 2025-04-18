from typing import Generator

import uvicorn
from fastapi import FastAPI
from fastapi.responses import StreamingResponse
from llama_cpp import Llama
from pydantic import BaseModel

app = FastAPI()

# Load the model (change path to your GGUF file)
model_path = "/home/tik/.local/share/nomic.ai/GPT4All/Llama-3.2-3B-Instruct-Q4_0.gguf"
llm = Llama(
    model_path=model_path,
    n_ctx=2048,
    # n_threads=os.cpu_count(),
    n_threads=4,
    verbose=False
)

class ChatRequest(BaseModel):
    prompt: str
    max_tokens: int = 512
    temperature: float = 0.7


def stream_chat(request: ChatRequest) -> Generator[str]:
    response_text = ""
    for output in llm(
            request.prompt,
            max_tokens=request.max_tokens,
            temperature=request.temperature,
            stop=["</s>", "USER:", "ASSISTANT:"],
            stream=True  # Enable streaming
    ):
        response_text += output["choices"][0]["text"]
        yield output["choices"][0]["text"]


@app.post("/chat")
async def chat(request: ChatRequest):
    return StreamingResponse(
        stream_chat(request),
        media_type="text/event-stream",
    )


if __name__ == '__main__':
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)
