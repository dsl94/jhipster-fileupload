(function() {
    'use strict';

    angular
        .module('fileuploadApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance','DataUtils', 'entity', 'Student'];

    function StudentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Student) {
        var vm = this;

        vm.student = entity;
        vm.clear = clear;
        vm.save = save;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fileuploadApp:studentUpdate', result);
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
