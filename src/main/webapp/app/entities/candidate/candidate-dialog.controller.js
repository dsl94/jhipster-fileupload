(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('CandidateDialogController', CandidateDialogController);

    CandidateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Candidate'];

    function CandidateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Candidate) {
        var vm = this;

        vm.candidate = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.candidate.id !== null) {
                Candidate.update(vm.candidate, onSaveSuccess, onSaveError);
            } else {
                Candidate.save(vm.candidate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fileuploadApp:candidateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCvFile = function ($file, candidate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        candidate.cvFile = base64Data;
                        candidate.cvFileContentType = $file.type;
                    });
                });
            }
        };

    }
})();
