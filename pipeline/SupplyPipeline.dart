import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:io';

Future<List<dynamic>> extract(String filename) async {
  final file = File(filename);
  String text = await file.readAsString();

  List<dynamic> data = jsonDecode(text);
  return data;
}

Future<List<double>> transform(String endpoint, Map<String, String> jsonData) async {
  final url = Uri.parse('$endpoint/embedding');

  Future<List<double>> embedding = Future.value([]);
  
  try {
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(jsonData),
    );

    if (response.statusCode == 200 || response.statusCode == 201) {
      final responseData = jsonDecode(response.body);
      final embeddingDynamic = responseData["vector"];
      List<double> embeddingDouble = embeddingDynamic.cast<double>();
      embedding = Future.value(embeddingDouble);

    } else {
      print('Request failed with status: ${response.statusCode}');
      print('Response body: ${response.body}');
    }
  } catch (e) {
    print('An error occurred: $e');
  }

  return embedding;
}

void load(String endpoint, Map<String, dynamic> row, List<double> embedding) async {
  final url = Uri.parse('$endpoint/supply');
  
  final jsonData = {
    "supplyId": "",
    "partyId": row["partyId"],
    "title": row["title"],
    "price": 10.1,
    "description": row["description"],
    "embedding": embedding
  };

  try {
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(jsonData),
    );

    if (response.statusCode == 200 || response.statusCode == 201) {
      final responseData = jsonDecode(response.body);
      print('Response data: $responseData');
    } else {
      print('Request failed with status: ${response.statusCode}');
      print('Response body: ${response.body}');
    }
  } catch (e) {
    print('An error occurred: $e');
  }
}

void main() async {
  final endpoint = 'http://localhost:8080/api';

  final data = await extract('supplies.json');
  
  for(var row in data) {
    final embedding = await transform(endpoint, {"text": row["description"]});
    load(endpoint, row, embedding);
  }
}
