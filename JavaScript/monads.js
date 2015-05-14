//Monads
function MONAD(modifier) {
    var prototype = Object.create(null);
    function unit(value) {
        var monad = Object.create(prototype);
        monad.bind = function(func, args) {
            return func.apply(undefined, [value].concat(Array.prototype.slice.apply(args || [])));
        };
        if (typeof modifier === 'function') {
            modifier(monad, value);
        }
        return monad;
    };
    unit.lift = function(name, func) {
        prototype[name] = function(...args) {
            return unit(this.bind(func, args));
        };
        return unit;
    };
    return unit;
}

//Utility functions
function describe(testName, testBody) {
    console.log("Testing [" + testName + "]...");
    testBody();
    console.log("\n");
}

function assertEquals(expected, gotIt) {
    var condition = expected === gotIt;
    if (!condition) {
        console.log('=> [Assertion failed] ' + expected + " === " + gotIt);
    } 
    else {
        console.log('=> [Assertion ok] ' + expected + " === " + gotIt);
    }
}

function assertThrows(func) {
    try {
        func();
        console.log('=> [Assertion failed] Should throw an exception ');
    }
    catch(e) {
        console.log('=> [Assertion ok] Exception thrown: "' + e + '"');
    }
}

function identity(x) { 
    return x; 
};

//Testing...
describe('Ajax monad', function () {
    var ajax = MONAD().lift('identity', identity).lift('alert', alert).lift('log', console.log);
    var monad = ajax('Hello, World');
    assertEquals('Hello, World', monad.bind(identity));
    //assertEquals('Hello, World', monad.identity());
    //monad.log(); 
    //monad.bind(console.log);
    //monad.alert();
    //monad.bind(alert);    
});

describe('Maybe monad', function() {
    var maybe = MONAD(function(monad, value) {
        if (value === null || value === undefined) {
            monad.is_null = true;
            monad.bind = function() {
                return monad;
            };
        }
    });
    var monad = maybe(null);
    var result = monad.bind(console.log);
    assertEquals(true, result.is_null);dog
});