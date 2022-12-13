from flask import Flask, render_template, url_for, request, redirect
import sys
import pandas as pd
import numpy as np
from flask_cors import CORS

from learning_spaces.kst import iita
import pandas as pd
app = Flask(__name__)
CORS(app)

@app.route('/')
def index():
    data_frame = pd.DataFrame({'a': [1, 1, 1], 'b': [1, 1, 1], 'c': [1, 1, 1]})
    print(data_frame)
    response = iita(data_frame, v=1)
    print(response)
    print(pd.Series(response).to_json(orient='values'))
    return pd.Series(response).to_json()

@app.route('/iita', methods=['POST'])
def iitaEndpoint():
    request_data = request.get_json()
    data_frame = pd.DataFrame(request_data)
    data_frame.index = np.arange(1, len(data_frame) + 1)
    print(data_frame)
    response = iita(data_frame, v=1)
    res = response
    res['concepts'] = data_frame.columns
    return pd.Series(res).to_json()