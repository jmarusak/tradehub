import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:io';

Future<List<dynamic>> extract(String filename) async {
  final file = File(filename);
  String text = await file.readAsString();

  List<dynamic> data = jsonDecode(text);
  return data;
}

void load(String endpoint, Map<String, dynamic> row) async {
  final url = Uri.parse('$endpoint/parties');
  
  final jsonData = {
    "partyId": row["partyId"],
    "name": row["name"]
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

  final data = await extract('parties.json');

  for(var row in data) {
    load(endpoint, row);
  }
}
