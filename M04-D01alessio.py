#from functools import reduce

def add_element(a_set, elem):
    if elem > 0:
        a_set.add(elem)
    return a_set

def remove_negatives(a_set):
    return {elem for elem in a_set if elem > 0}

def sum_elements(a_set):
    pass
#    reduce(lambda x, y: x + y, a_set)