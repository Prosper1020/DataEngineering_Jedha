# Create a method that adds an element to a set, only if it is a positive value.
# Create a method that removes negative values from a set.
# Create a method that returns the sum of all elements in a set.


import pytest

def add_element(el, my_set):
    if el is None: return my_set
    if (el > 0):
        my_set.add(el)
    return my_set

def remove_negative(my_set):
    return {el for el in my_set if el > 0}

def sum_set(my_set):
    return sum(my_set)


@pytest.fixture()
def array_fixture():
    return {-1, 1, 2, 3}

def test_adding(array_fixture):
    arr = add_element(10, array_fixture)
    assert len(arr) == 5
    assert 10 in arr

def test_adding_limit(array_fixture):
    arr = add_element(None, array_fixture)
    assert len(arr) == 4


def test_negative(array_fixture):
    arr = remove_negative(array_fixture)
    assert len(arr) == 3
    assert type(arr) == type(set())
    
def test_sum(array_fixture):
    res = sum_set(array_fixture)
    assert res == 5

def test_empty(array_fixture=[]):
    assert len(array_fixture) == 0