#include "DynamicArray.h"

DynamicArray* CreateArray(int maxLength)
{
    DynamicArray* array = (DynamicArray*)malloc(sizeof(DynamicArray));

    array->maxLength = maxLength;
    array->length = 0;

    array->elements = (TElement*)malloc(maxLength * sizeof(TElement));

    return array;
}

void DuplicateArray(DynamicArray source, DynamicArray* destination)
{
    TElement* ptr = realloc(destination->elements, source.maxLength * sizeof(TElement));
    destination->elements = ptr;
  
    Copy(source, destination);
}

void DestroyArray(DynamicArray* array)
{
    free(array->elements);
    array->elements = NULL;

    free(array);
}

void ReallocateArray(DynamicArray* array, int newMaxLength)
{
    TElement* ptr = realloc(array->elements, newMaxLength * sizeof(TElement));
    array->maxLength = newMaxLength;
    array->elements = (TElement*)ptr;
}

void AddElement(DynamicArray* array, TElement newElement)
{
    if (array->length == array->maxLength)
        ReallocateArray(array, 2 * array->length);
    array->elements[array->length++] = newElement;
}

void RemoveElement(DynamicArray* array, int position)
{
    for (int i = position; i < array->length; ++i)
        array->elements[i] = array->elements[i + 1];

    array->length--;
}

void Copy(DynamicArray sourceArray, DynamicArray* destinationArray)
{
    destinationArray->maxLength = sourceArray.maxLength;
    destinationArray->length = sourceArray.length;

    for (int i = 0; i < sourceArray.length; ++i)
        Assign(sourceArray.elements[i], &destinationArray->elements[i]);
}
