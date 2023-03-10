#pragma once

#include "Participant.h"
#include <stddef.h>
#include <stdlib.h>

typedef Participant TElement;

typedef struct
{
	TElement* elements;
	int length;
	int maxLength;
} DynamicArray;

/// <summary>
/// Constructor for a DynamicArray object
/// </summary>
/// <param name="maxLength">The maximum size of the DynamicArray</param>
/// <returns>The new DynamicArray object</returns>
DynamicArray* CreateArray(int maxLength);

/// <summary>
/// Copy constructor for a DynamicArray object
/// </summary>
/// <param name="source">The array to be copied</param>
/// <returns>A copy of the given DynamicArray</returns>
void DuplicateArray(DynamicArray source, DynamicArray* destination);

/// <summary>
/// Destructor for a DynamicArray
/// </summary>
/// <param name="array">The array to be destroyed</param>
void DestroyArray(DynamicArray* array);


/// <summary>
/// Adds a new element to the DynamicArray
/// </summary>
/// <param name="array">The dynamic array where to add the element</param>
/// <param name="newParticipant">The new participant to be added</param>
void AddElement(DynamicArray* array, TElement newElement);

/// <summary>
/// Removes an element from the DynamicArray
/// </summary>
/// <param name="array">The dynamic array from where to remove the element</param>
/// <param name="position">The position of the element</param>
void RemoveElement(DynamicArray* array, int position);

/// <summary>
/// Copies the elements of the source array in the destination array
/// </summary>
/// <param name="sourceArray">The source array</param>
/// <param name="destinationArray">The destination array</param>
void Copy(DynamicArray sourceArray, DynamicArray* destinationArray);