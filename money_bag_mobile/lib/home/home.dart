import 'package:flutter/material.dart';
import 'package:money_bag_mobile/home/apps.dart';

class Home extends StatefulWidget {
  const Home(this.switchActivity, {super.key});

  final void Function(String) switchActivity;

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: double.infinity,
      child: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(
              height: 1000,
              width: 400,
              child: GridView.builder(
                padding: const EdgeInsets.all(16),
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 3, // Number of columns
                  crossAxisSpacing: 10, // Spacing between columns
                  mainAxisSpacing: 10, // Spacing between rows
                ),
                itemCount: applications.length, // Number of items in the grid
                itemBuilder: (context, index) {
                  final app = applications[index];
                  return GestureDetector(
                    onTap: () {
                      // Handle navigation or action
                      print('Selected: ${app.appName}');
                      widget.switchActivity(app.appId);
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        // color: Colors.white, // Item background color
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Image.asset(app.icon, width: 50, height: 50),
                          // App icon
                          const SizedBox(height: 8),
                          Text(
                            app.appName,
                            // style: const TextStyle(color: Colors.white),
                            textAlign: TextAlign.center,
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
