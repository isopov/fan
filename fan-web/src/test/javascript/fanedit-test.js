'use strict';

describe('Edit controllers', function () {

    describe('IndexCtrl', function () {

        it('should have two messages', function () {
            var scope = {},
                ctrl = new IndexCtrl(scope);

            expect(scope.messages.length).toBe(2);
        });
    });
});
