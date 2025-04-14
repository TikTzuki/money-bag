import 'package:flutter/material.dart';
import 'package:money_bag_mobile/home/apps.dart';

import 'home/home.dart';

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
var kDarkColorScheme = ColorScheme.fromSeed(
  brightness: Brightness.dark,
  seedColor: const Color.fromRGBO(27, 27, 47, 1),
);
class AppTheme {
  static const Color primaryColor = Color.fromRGBO(22, 36, 71, 1);
  static const Color secondaryColor = Color.fromRGBO(31, 64, 104, 1);
  static const Color backgroundColor = Color.fromRGBO(27, 27, 47, 1);
  static const Color accentColor = Color.fromRGBO(228, 63, 90, 1);

  static ThemeData get theme {
    return ThemeData(
      primaryColor: primaryColor,
      scaffoldBackgroundColor: backgroundColor,
      appBarTheme: AppBarTheme(
        backgroundColor: primaryColor,
        foregroundColor: Colors.white,
      ),
      textTheme: const TextTheme(
        bodyLarge: TextStyle(color: Colors.white),
        bodyMedium: TextStyle(color: Colors.white),
        bodySmall: TextStyle(color: Colors.white),
      ),
      buttonTheme: ButtonThemeData(
        buttonColor: accentColor,
        textTheme: ButtonTextTheme.primary,
      ),
      outlinedButtonTheme: OutlinedButtonThemeData(
        style: OutlinedButton.styleFrom(
          foregroundColor: Colors.white,
          backgroundColor: accentColor,
        ),
      ),
    );
  }

  static ThemeData get darkTheme {
    return ThemeData.dark().copyWith(
      useMaterial3: true,
      colorScheme: kDarkColorScheme,
      cardTheme: const CardTheme().copyWith(
        color: kDarkColorScheme.secondaryContainer,
        margin: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 8,
        ),
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: kDarkColorScheme.primaryContainer,
          foregroundColor: kDarkColorScheme.onPrimaryContainer,
        ),
      ),
    );
  }
}

class _MainAppState extends State<MainApp> {
  String activeApp = "home";

  Map<String, Function> apps = {};

  void switchActivity(String activity) {
    setState(() {
      activeApp = activity;
    });
  }

  void backToHome() {
    setState(() {
      activeApp = "home";
    });
  }

  @override
  void initState() {
    for (var item in applications) {
      apps[item.appId] = item.constructor;
    }
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var body = activeApp == "home" ? Home(switchActivity) : apps[activeApp]!();
    return MaterialApp(
      theme: AppTheme.theme,
      darkTheme: ThemeData.dark().copyWith(),
      home: Scaffold(
        appBar: AppBar(
          title: Text(activeApp),
          leading:
              activeApp != "home"
                  ? IconButton(
                    icon: const Icon(Icons.arrow_back),
                    onPressed: backToHome,
                  )
                  : null,
        ),
        body: body,
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}
