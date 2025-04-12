import 'package:flutter/material.dart';
import 'package:money_bag_mobile/quiz/questions.dart';

import 'answer_summary.dart';

class ResultScreen extends StatelessWidget {
  ResultScreen(this.chosenAnswers, this.backToHome, {super.key});

  final void Function() backToHome;
  final List<String> chosenAnswers;

  List<Map<String, Object>> summarizeData() {
    List<Map<String, Object>> summary = [];
    for (var i = 0; i < chosenAnswers.length; i++) {
      summary.add({
        "question_index": i,
        "question": questions[i].text,
        "correct_answer": questions[i].answers[0],
        "user_answer": chosenAnswers[i],
      });
    }
    return summary;
  }

  @override
  Widget build(BuildContext context) {
    final summary = summarizeData();
    final totalQuestions = questions.length;
    final numCorrectQuestions =
        summary.where((it) {
          return it["user_answer"] == it["correct_answer"];
        }).length;
    return SizedBox(
      width: double.infinity,
      child: Container(
        margin: const EdgeInsets.all(40),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'You answered $numCorrectQuestions out of $totalQuestions questions correctly!',
            ),
            const SizedBox(height: 30),
            AnswerSummary(summary),
            const SizedBox(height: 30),
            TextButton(
              onPressed: backToHome,
              style: TextButton.styleFrom(
                backgroundColor: Colors.blueAccent,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(
                  vertical: 12,
                  horizontal: 24,
                ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
                textStyle: const TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              child: const Text('Restart Quiz!'),
            ),
          ],
        ),
      ),
    );
  }
}
