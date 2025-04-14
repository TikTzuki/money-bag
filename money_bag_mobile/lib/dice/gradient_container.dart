import 'package:flutter/material.dart';
import 'package:money_bag_mobile/dice/dice_roller.dart';

const startAlignment = Alignment.topLeft;
const endAlignment = Alignment.bottomRight;

class DiceApp extends StatelessWidget {
  const DiceApp(this.color1, this.color2, this.switchActivity, {super.key});

  const DiceApp.purple(this.switchActivity, {super.key})
    : color1 = Colors.deepPurple,
      color2 = Colors.indigo;

  final Function switchActivity;
  final Color color1;
  final Color color2;

  @override
  Widget build(context) {
    return Center(child: DiceRoller(switchActivity));
  }
}
