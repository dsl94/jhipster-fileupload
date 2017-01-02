(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('CandidateController', CandidateController);

    CandidateController.$inject = ['$scope', '$state', 'DataUtils', 'Candidate'];

    function CandidateController ($scope, $state, DataUtils, Candidate) {
        var vm = this;

        vm.candidates = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Candidate.query(function(result) {
                vm.candidates = result;
            });
        }
    }
})();
