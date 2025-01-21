import 'package:http/http.dart' as http;
import 'dart:convert';

class HttpClient {
  final String baseUrl;

  HttpClient(this.baseUrl);

  Future<void> get(String endpoint) async {
    final url = Uri.parse('$baseUrl$endpoint');

    try {
      final response = await http.get(url);

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print('Response data: $data');
      } else {
        print('Request failed with status: ${response.statusCode}');
      }
    } catch (e) {
      print('An error occurred: $e');
    }
  }
}

void main() {
  final client = HttpClient('http://localhost:8080');
  client.get('/api/parties');
}
