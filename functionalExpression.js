"use strict";

var VARS = {
    "x": 0,
    "y": 1,
    "z": 2
};

function unaryOperation (opType) {
    return function (operand) {
        return function () {
            return opType(operand.apply(null, arguments));
        }
    }
}

function binaryOperation (opType) {
    return function (first, second) {
        return function () {
            return opType(first.apply(null, arguments), second.apply(null, arguments));
        }
    }
}

function cnst (val) {
    return function () {
        return val;
    };
}

var variable = function (name) {
    return function () {
        return arguments[VARS[name]];
    }
};

var add = binaryOperation(function (first, second) {
    return first + second;
});

var subtract = binaryOperation(function (first, second) {
    return first - second;
});

var multiply = binaryOperation(function (first, second) {
    return first * second;
});

var divide = binaryOperation(function (first, second) {
    return first / second;
});

var negate = unaryOperation(function (operand) {
    return -operand;
});

var cube = unaryOperation(function (operand) {
    return operand * operand * operand;
});

var cuberoot = unaryOperation(function (operand) {
    return Math.pow(operand, (1 / 3));
});
