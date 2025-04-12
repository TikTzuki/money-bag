import 'dart:math';

import 'package:flutter/material.dart';

class DiceRoller extends StatefulWidget {
  const DiceRoller(this.switchActivity, {super.key});

  final Function switchActivity;

  @override
  State<DiceRoller> createState() {
    return _DiceRollerState();
  }
}

class _DiceRollerState extends State<DiceRoller> {
  var activeDice = "assets/images/dices/dice-2.png";

  void rollDice() {
    setState(() {
      var diceValue = Random().nextInt(6) + 1;
      activeDice = "assets/images/dices/dice-$diceValue.png";
      print(activeDice);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Image.asset(activeDice, width: 200),
        const SizedBox(height: 10),
        TextButton(
          onPressed: rollDice,
          style: TextButton.styleFrom(
            foregroundColor: Colors.white,
            textStyle: const TextStyle(fontSize: 28),
          ),
          child: const Text("Roll Dice"),
        ),
        OutlinedButton(
          onPressed: () {
            widget.switchActivity();
          },
          child: Text("switch"),
        ),
      ],
    );
  }
}
