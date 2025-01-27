import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import 'dart:convert';

class Api {
  static const String baseUrl = 'http://localhost:8080/api';

  static Future<bool> saveDemand({
    required String email,
    required String title,
    required String description,
    required String buySell,
  }) async {

    final logger = Logger();

    // Generate embedding vector for description
    final embedding = await getEmbedding(text: description);

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/demand'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'supplyId': '',
          'partyId': email,
          'title': title,
          'description': description,
          'embedding': embedding,
        }),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        return true;
      } else {
        logger.e('Failed to submit item. Status code: ${response.statusCode}');
        return false;
      }
    } catch (e) {
      logger.e('Error submitting item: $e');
      return false;
    }
  }

  static Future<List<double>> getEmbedding({ required String text }) async {
    Future<List<double>> embedding = Future.value([]);
    
    final logger = Logger();
    
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/embedding'),
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
        logger.e('Failed to submit item. Status code: ${response.statusCode}');
      }
    } catch (e) {
      logger.e('Error submitting item: $e');
    }
    return embedding;
  }
} 