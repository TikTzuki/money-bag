import 'package:flutter/material.dart';
import 'package:money_bag_mobile/dice/dice_roller.dart';

const startAlignment = Alignment.topLeft;
const endAlignment = Alignment.bottomRight;

class GradientContainer extends StatelessWidget {
  const GradientContainer(
    this.color1,
    this.color2,
    this.switchActivity, {
    super.key,
  });

  const GradientContainer.purple(this.switchActivity, {super.key})
    : color1 = Colors.deepPurple,
      color2 = Colors.indigo;

  final Function switchActivity;
  final Color color1;
  final Color color2;

  @override
  Widget build(context) {
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [color1, color2],
          begin: startAlignment,
          end: endAlignment,
        ),
      ),
      child: Center(child: DiceRoller(switchActivity)),
    );
  }
}
