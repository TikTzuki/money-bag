import 'package:flutter/cupertino.dart';



class ChartApp extends StatefulWidget {
  const ChartApp({Key? key}) : super(key: key); // Added constructor

  @override
  State<StatefulWidget> createState() {
    return _ChartAppState();
  }
}
class _ChartAppState extends State<ChartApp> {
  @override
  Widget build(BuildContext context) {
    return Container(child: Center(child: Text('ChartApp State')));
  }
}
