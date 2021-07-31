# my_concatenate.py
from unittest import mock
from my_concatenate import concatenate


@mock.patch("my_concatenate.random.randint", return_value=2, autospec=True)
def test_concatenate(mock): 
    assert concatenate() == "Hello stranger 2"  
    mock.assert_called_once_with(0, 9)