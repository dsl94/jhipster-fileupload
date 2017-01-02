(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$scope', '$state', 'Student'];

    function StudentController ($scope, $state, Student) {
        var vm = this;

        vm.students = [];

        loadAll();

        function loadAll() {
            Student.query(function(result) {
                vm.students = result;
            });
        }
    }
})();
