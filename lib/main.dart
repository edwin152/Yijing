import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const YijingApp());
}

class YijingApp extends StatelessWidget {
  const YijingApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '易经占卜',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const DivinationPage(),
    );
  }
}

class DivinationPage extends StatefulWidget {
  const DivinationPage({super.key});

  @override
  State<DivinationPage> createState() => _DivinationPageState();
}

class _DivinationPageState extends State<DivinationPage> {
  final Random _random = Random();
  final List<YaoLine> _lines = <YaoLine>[];

  String _guaName = '';
  String _guaText = '';
  bool _loading = false;

  Future<void> _divine() async {
    setState(() {
      _loading = true;
      _lines
        ..clear()
        ..addAll(List<YaoLine>.generate(6, (_) => YaoLine.random(_random)));
    });

    final String text = await rootBundle.loadString('app/src/main/assets/_64.txt');
    final List<String> blocks = text
        .trim()
        .split('\n\n')
        .where((String item) => item.trim().isNotEmpty)
        .toList();

    final int index = _toGuaIndex(_lines);
    final List<String> rows = blocks[index].split('\n');

    setState(() {
      _guaName = rows.length > 1 ? rows[1].trim() : '未知卦象';
      _guaText = rows.skip(3).take(4).join('\n');
      _loading = false;
    });
  }

  int _toGuaIndex(List<YaoLine> lines) {
    int value = 0;
    for (int i = 0; i < lines.length; i++) {
      if (lines[i].isYang) {
        value |= (1 << i);
      }
    }
    return value % 64;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('易经占卜')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            FilledButton.icon(
              onPressed: _loading ? null : _divine,
              icon: const Icon(Icons.casino),
              label: const Text('开始起卦'),
            ),
            const SizedBox(height: 20),
            if (_lines.isNotEmpty) ...<Widget>[
              const Text('卦象（自下而上）', style: TextStyle(fontWeight: FontWeight.bold)),
              const SizedBox(height: 12),
              ..._lines
                  .asMap()
                  .entries
                  .map((MapEntry<int, YaoLine> entry) => _YaoWidget(line: entry.value, index: entry.key + 1)),
              const Divider(height: 28),
            ],
            if (_guaName.isNotEmpty)
              Text(_guaName, style: Theme.of(context).textTheme.headlineMedium),
            if (_guaText.isNotEmpty) ...<Widget>[
              const SizedBox(height: 8),
              Text(_guaText),
            ],
          ],
        ),
      ),
    );
  }
}

class _YaoWidget extends StatelessWidget {
  const _YaoWidget({required this.line, required this.index});

  final YaoLine line;
  final int index;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: <Widget>[
          Text('第$index爻：'),
          const SizedBox(width: 8),
          Expanded(
            child: Container(
              height: 14,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(6),
                color: line.isYang ? Colors.black87 : Colors.black45,
              ),
              child: line.isYang
                  ? null
                  : Row(
                      children: <Widget>[
                        Expanded(child: Container(color: Colors.black45)),
                        Container(width: 18, color: Colors.white),
                        Expanded(child: Container(color: Colors.black45)),
                      ],
                    ),
            ),
          ),
          const SizedBox(width: 8),
          Text(line.isChanging ? '变' : '静'),
        ],
      ),
    );
  }
}

class YaoLine {
  YaoLine({required this.isYang, required this.isChanging});

  final bool isYang;
  final bool isChanging;

  factory YaoLine.random(Random random) {
    final int roll = random.nextInt(4);
    return YaoLine(
      isYang: roll == 1 || roll == 3,
      isChanging: roll == 0 || roll == 3,
    );
  }
}
