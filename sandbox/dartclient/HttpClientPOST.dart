import 'package:http/http.dart' as http;
import 'dart:convert';

class HttpClient {
  final String baseUrl;

  HttpClient(this.baseUrl);

  Future<void> postData(String endpoint, Map<String, dynamic> jsonData) async {
    final url = Uri.parse('$baseUrl$endpoint');

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
}

void main() {
  final client = HttpClient('http://localhost:8080/api');

  final jsonData = {
    "supplyId": "",
    "partyId": "3",
    "title": "wrap",
    "price": 11.22,
    "description": "corn wrap",
    "embedding": [1.2, 22.2, 0.4]
  };

  client.postData('/supplies', jsonData);
}
