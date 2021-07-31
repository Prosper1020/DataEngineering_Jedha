import pytest
from alessio import add_element, remove_negatives, sum_elements

@pytest.fixture()
def a_set_fixture():
    return {-1, 1, 2}

def test_add_positive_element(a_set_fixture):
    res = add_element(a_set_fixture, 7)
    assert len(res) == 4
    assert 7 in res

def test_remove_negatives(a_set_fixture):
    res = remove_negatives(a_set_fixture)
    assert len(res) == 2

def test_sum_elements(a_set_fixture):
    res = sum_elements(a_set_fixture)
    assert res == 2


