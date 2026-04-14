import 'package:flutter_test/flutter_test.dart';
import 'package:yijing_flutter/main.dart';

void main() {
  testWidgets('renders divination button', (WidgetTester tester) async {
    await tester.pumpWidget(const YijingApp());

    expect(find.text('开始起卦'), findsOneWidget);
  });
}
