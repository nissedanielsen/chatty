# app.py
from flask import Flask, request, jsonify
from transformers import GPT2LMHeadModel, GPT2Tokenizer

app = Flask(__name__)

# Load pre-trained model and tokenizer
model_name = "gpt2"  # Or any other model you prefer
model = GPT2LMHeadModel.from_pretrained(model_name)
tokenizer = GPT2Tokenizer.from_pretrained(model_name)

@app.route('/generate', methods=['POST'])
def generate():
    data = request.get_json()
    prompt = data.get("input", "")

    if not prompt:
        return jsonify({"error": "No input provided"}), 400

    inputs = tokenizer(prompt, return_tensors="pt")
    outputs = model.generate(inputs["input_ids"], max_length=100)
    generated_text = tokenizer.decode(outputs[0], skip_special_tokens=True)

    return jsonify({"response": generated_text})

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
