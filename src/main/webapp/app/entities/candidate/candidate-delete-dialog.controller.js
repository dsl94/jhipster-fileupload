(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('CandidateDeleteController',CandidateDeleteController);

    CandidateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Candidate'];

    function CandidateDeleteController($uibModalInstance, entity, Candidate) {
        var vm = this;

        vm.candidate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Candidate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
