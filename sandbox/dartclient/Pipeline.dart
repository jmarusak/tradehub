import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:io';

Future<List<dynamic>> extract(String filename) async {
  final file = File(filename);
  String text = await file.readAsString();

  List<dynamic> list = jsonDecode(text);
  return list;
}

Future<List<double>> transform(String endpoint, Map<String, String> jsonData) async {
  final url = Uri.parse('$endpoint/embeddings');

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

void load(String endpoint, String title, String description, List<double> embedding) async {
  final url = Uri.parse('$endpoint/supplies');
  
  final jsonData = {
    "supplyId": "",
    "partyId": "",
    "title": title,
    "price": 10.1,
    "description": description,
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

  final json = await extract('furniture.json');
  
  for(var item in json) {
    final embedding = await transform(endpoint, {"text": item["description"]});
    load(endpoint, item["title"], item["description"], embedding);
  }
}
