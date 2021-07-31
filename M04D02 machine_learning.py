from joblib import dump, load
from sklearn import svm
from sklearn import datasets


def train_model():
    model = svm.SVC()
    X, y = datasets.load_digits(return_X_y=True)
    model.fit(X, y)
    return model

def export_model(model):
    dump(model, './digits_model.joblib')

def start():
    model = train_model()
    export_model(model)
    print('Model successfully exported.')

def load_model():
    model = load('./digits_model.joblib')
    return model
    
if __name__ == '__main__':
    start()
    
