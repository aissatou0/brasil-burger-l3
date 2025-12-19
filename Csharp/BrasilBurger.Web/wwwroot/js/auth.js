
        function switchTab(tabName) {
            // Remove active from all tabs and contents
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
            document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
            
            // Add active to selected
            event.target.classList.add('active');
            document.getElementById(tabName + '-tab').classList.add('active');
        }
   