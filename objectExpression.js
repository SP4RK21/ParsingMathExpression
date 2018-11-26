"use strict";

var VARS = {"x": 0, "y": 1, "z": 2};

function Const(value) {
    this.value = value;
}

Const.prototype = {
    toString: function () {
        return this.value.toString();
    },
    evaluate: function () {
        return this.value;
    }
};

function Variable(name) {
    this.name = name;
}

Variable.prototype = {
    toString: function () {
        return this.name;
    },
    evaluate: function () {
        return arguments[VARS[this.name]];
    }
};

var OpPrototype = {
    toString: function () {
        var str = "";
        str += this.operands[0];
        for (var i = 1; i < this.operands.length; i++) {
            str += (" " + this.operands[i]);
        }
        str += (' ' + this.signature);
        return str;
    },
    evaluate: function () {
        var res = [];
        var op = this.operands;
        for (var i = 0; i < op.length; i++) {
            res.push(op[i].evaluate.apply(op[i], arguments));
        }
        return this.operationType.apply(null, res);
    }
};

function Operation(operationType, signature) {
    var res = function () {
        this.operands = Array.prototype.slice.call(arguments);
        this.operationType = operationType;
        this.signature = signature;
    };
    res.prototype = OpPrototype;
    return res;
}

var Add = Operation(function (first, second) {
    return first + second
}, "+");

var Subtract = Operation(function (first, second) {
    return first - second
}, "-");

var Multiply = Operation(function (first, second) {
    return first * second
}, "*");

var Divide = Operation(function (first, second) {
    return first / second
}, "/");

var Negate = Operation(function (first) {
    return -first
}, "negate");

var Square = Operation(function (first) {
    return first * first
}, "square");

var Sqrt = Operation(function (first) {
    return Math.sqrt(Math.abs(first))
}, "sqrt");