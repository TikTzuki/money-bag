import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:money_bag_mobile/quiz/questions.dart';

class QuestionScreen extends StatefulWidget {
  QuestionScreen(this.answer, {super.key});

  void Function(String answer) answer;

  @override
  State<QuestionScreen> createState() => _QuestionScreenState();
}

class _QuestionScreenState extends State<QuestionScreen> {
  var questionIndex = 0;

  void answerQuestion(String answer) {
    this.widget.answer(answer);
    setState(() {
      questionIndex++;
    });
  }

  @override
  Widget build(BuildContext context) {
    var currentQuestion = questions[questionIndex];
    List<String> answers = List.from(currentQuestion.answers);
    answers.shuffle();

    return SizedBox(
      width: double.infinity,
      child: Container(
        margin: EdgeInsets.all(40),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              currentQuestion.text,
              style: GoogleFonts.lato(
                // color: Color.fromARGB(255, 201, 153, 251),
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 30),
            ...answers.map((it) {
              return ElevatedButton(
                onPressed: () {
                  answerQuestion(it);
                },
                child: Text(it),
              );
            }),
          ],
        ),
      ),
    );
  }
}
