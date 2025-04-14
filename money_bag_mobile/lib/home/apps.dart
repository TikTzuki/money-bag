import 'package:money_bag_mobile/chart/chart_app.dart';
import 'package:money_bag_mobile/dice/gradient_container.dart';
import 'package:money_bag_mobile/quiz/quiz_app.dart';

import 'app_model.dart';

var applications = [
  AppModel(
    appId: 'dice',
    appName: 'Dice roller',
    icon: 'assets/icons/rolling-dices.png',
    constructor: () {
      return DiceApp.purple(() {});
    },
  ),
  AppModel(
    appId: 'quiz',
    appName: 'Quiz',
    icon: 'assets/icons/rune-stone.png',
    constructor: () {
      return QuizApp(() {});
    },
  ),
  AppModel(
    appId: 'chart',
    appName: 'Chart',
    icon: 'assets/icons/chart.png',
    constructor: () {
      return ChartApp();
    },
  ),
];
