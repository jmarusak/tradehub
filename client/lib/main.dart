import 'package:flutter/material.dart';
import 'package:tradehub/api.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: ItemFormPage(),
    );
  }
}

class ItemFormPage extends StatefulWidget {
  const ItemFormPage({super.key});

  @override
  ItemFormPageState createState() => ItemFormPageState();
}

class ItemFormPageState extends State<ItemFormPage> {
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
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      _formKey.currentState!.save();
                      
                      final success = await Api.saveDemand(
                        email: email,
                        title: itemTitle,
                        description: itemDescription,
                        buySell: buySell,
                      );

                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text(
                            success 
                              ? 'Form submitted successfully!' 
                              : 'Failed to submit form. Please try again.'
                          ),
                        ),
                      );
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
