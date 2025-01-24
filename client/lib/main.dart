import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: ItemFormPage(),
    );
  }
}

class ItemFormPage extends StatefulWidget {
  @override
  _ItemFormPageState createState() => _ItemFormPageState();
}

class _ItemFormPageState extends State<ItemFormPage> {
  final _formKey = GlobalKey<FormState>();
  String email = '';
  String itemTitle = '';
  String itemDescription = '';
  String buySell = 'Buy';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Item Form'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Email Input
              TextFormField(
                decoration: InputDecoration(
                  labelText: 'Email Address',
                  border: OutlineInputBorder(),
                ),
                keyboardType: TextInputType.emailAddress,
                onSaved: (value) => email = value ?? '',
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter your email address';
                  }
                  return null;
                },
              ),
              SizedBox(height: 16),

              // Item Title Input
              TextFormField(
                decoration: InputDecoration(
                  labelText: 'Item Title',
                  border: OutlineInputBorder(),
                ),
                onSaved: (value) => itemTitle = value ?? '',
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the item title';
                  }
                  return null;
                },
              ),
              SizedBox(height: 16),

              // Item Description Input
              TextFormField(
                decoration: InputDecoration(
                  labelText: 'Item Description',
                  border: OutlineInputBorder(),
                ),
                maxLines: 3,
                onSaved: (value) => itemDescription = value ?? '',
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the item description';
                  }
                  return null;
                },
              ),
              SizedBox(height: 16),

              // Buy/Sell Radio Buttons
              Text('Buy or Sell:'),
              Row(
                children: [
                  Expanded(
                    child: RadioListTile<String>(
                      title: Text('Buy'),
                      value: 'Buy',
                      groupValue: buySell,
                      onChanged: (value) {
                        setState(() {
                          buySell = value!;
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: RadioListTile<String>(
                      title: Text('Sell'),
                      value: 'Sell',
                      groupValue: buySell,
                      onChanged: (value) {
                        setState(() {
                          buySell = value!;
                        });
                      },
                    ),
                  ),
                ],
              ),

              // Submit Button
              SizedBox(height: 20),
              Center(
                child: ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      _formKey.currentState!.save();
                      // Process form data
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Form submitted successfully!')),
                      );
                      print('Email: $email');
                      print('Item Title: $itemTitle');
                      print('Item Description: $itemDescription');
                      print('Buy/Sell: $buySell');
                    }
                  },
                  child: Text('Submit'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
