import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import 'dart:convert';

class Api {
  static const String baseUrl = 'http://localhost:8080/api';

  static Future<List<double>> getEmbedding({ required String text }) async {
    Future<List<double>> embedding = Future.value([]);
    
    final logger = Logger();
    
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/embeddings'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'text': text
        }),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        final responseData = jsonDecode(response.body);

        final embeddingDynamic = responseData["vector"];
        List<double> embeddingDouble = embeddingDynamic.cast<double>();
        embedding = Future.value(embeddingDouble);
      } else {
        print('Failed to submit item. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error submitting item: $e');
    }

    return embedding;
  }
} 

void main() async {
  final embedding = await Api.getEmbedding(text: 'banana');
  print(embedding);
}
