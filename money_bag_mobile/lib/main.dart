import 'package:flutter/material.dart';
import 'package:money_bag_mobile/dice/gradient_container.dart';
import 'package:money_bag_mobile/quiz/quiz_app.dart';

void main() {
  runApp(MainApp());
}

class MainApp extends StatefulWidget {
  const MainApp({super.key});

  @override
  State<MainApp> createState() {
    return _MainAppState();
  }
}

class _MainAppState extends State<MainApp> {
  var activeActivity = "quiz-app";

  Map<String, Function> activities = {};

  void switchActivity(String activity) {
    setState(() {
      activeActivity = activity;
    });
  }

  Widget buildDiceRoller() {
    return GradientContainer.purple(() {
      switchActivity("quiz-app");
    });
  }

  Widget buildQuiz() {
    return QuizApp(() {
      switchActivity("quiz-app");
    });
  }

  @override
  void initState() {
    activities = {"dice-roller": buildDiceRoller, "quiz-app": buildQuiz};
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: Scaffold(body: activities[activeActivity]!()));
  }

  @override
  void dispose() {
    super.dispose();
  }
}
