import 'package:flutter/material.dart';
import 'package:money_bag_mobile/quiz/questions.dart';
import 'package:money_bag_mobile/quiz/questions_screen.dart';
import 'package:money_bag_mobile/quiz/result_screen.dart';

class QuizApp extends StatefulWidget {
  QuizApp(this.bankToHome, {super.key});

  Function bankToHome;

  @override
  State<QuizApp> createState() => _QuizAppState();
}

class _QuizAppState extends State<QuizApp> {
  var activeScreen = "home";
  List<String> selectedAnswers = [];

  Map<String, Function> widgets = {};

  void switchScreen(String activity) {
    setState(() {
      activeScreen = activity;
    });
  }

  void chooseAnswer(String answer) {
    selectedAnswers.add(answer);

    if (selectedAnswers.length == questions.length) {
      setState(() {
        activeScreen = "result";
      });
    }
  }

  @override
  void initState() {
    widgets = {
      "home": () {
        return SizedBox(
          width: double.infinity,
          child: Container(
            child: Column(
              children: [
                Image.asset('assets/images/dices/dice-1.png', width: 300),
                const SizedBox(height: 30),
                const Text("Quiz App"),
                const SizedBox(height: 30),
                OutlinedButton.icon(
                  onPressed: () {
                    switchScreen("questions");
                  },
                  // style: OutlinedButton.styleFrom(
                  //   foregroundColor: Colors.white,
                  //   backgroundColor: Colors.blue,
                  // ),
                  icon: const Icon(Icons.question_answer),
                  label: Text("Start Quiz"),
                ),
              ],
            ),
          ),
        );
      },
      "questions": () {
        return QuestionScreen(chooseAnswer);
      },
      "result": () {
        return ResultScreen(selectedAnswers, () {
          switchScreen("home");
        });
      },
    };
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        // gradient: LinearGradient(
        //   colors: [
        //     Color.fromARGB(255, 79, 46, 166),
        //     Color.fromARGB(255, 111, 69, 223),
        //   ],
        //   begin: Alignment.topLeft,
        //   end: Alignment.bottomRight,
        // ),
      ),
      child: widgets[activeScreen]!(),
    );
  }
}
