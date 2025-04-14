class AppModel {
  final String appId;
  final String appName;
  final String icon;
  final Function constructor;

  AppModel({
    required this.appId,
    required this.appName,
    required this.icon,
    required this.constructor,
  });
}
