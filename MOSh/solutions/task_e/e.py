#coding: utf-8
import os
import re

import sklearn
import numpy as np
import xgboost as xgb
from sklearn.pipeline import Pipeline
from sklearn.externals import joblib

from MOSh.solutions.a1 import STDOUTStream, FileStream


class Solver(object):

    def __init__(self, pipeline, classes):
        self.clf = pipeline
        self.classes = classes

    def as_one_hot(self, x):
        return [1 if x == c else 0 for c in self.classes]

    def as_class_label(self, x):
        return np.array(self.as_one_hot(x)).argmax()

    def from_label_to_class(self, label):
        return self.classes[int(label)]

    def classify(self, code):
        if isinstance(code, str):
            return self.from_label_to_class(self.clf.predict(np.array([code])))
        return [self.from_label_to_class(l) for l in self.clf.predict(code)]

    def __call__(self, code):
        return self.classify(code)


if __name__ == "__main__":
    filename = "E0"
    stream = STDOUTStream() # FileStream("ans.txt")
    stream2 = FileStream("../../answers/{}.txt".format(filename))

    path = "../../{}".format(filename)
    clf = joblib.load("xgb_text_clf_cpp_py_pas_0.99.pkl")
    solver = Solver(clf, classes=("C++", "Pascal", "Python"))
    for file in os.listdir(path):
        with open(os.path.abspath(os.path.join(path, "{}".format(file))), 'r') as f:
            stream2.writeln(solver('\n'.join(f.readlines())))
