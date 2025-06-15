/**
 * Relog Airlines Check-in System
 * Main JavaScript file for common functionality
 */

// Show error message
function showError(message, elementId = 'error-message') {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }
}

// Hide error message
function hideError(elementId = 'error-message') {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.style.display = 'none';
    }
}

// Show loading spinner
function showSpinner(spinnerId, buttonId = null) {
    const spinner = document.getElementById(spinnerId);
    if (spinner) {
        spinner.classList.remove('d-none');
    }
    
    if (buttonId) {
        const button = document.getElementById(buttonId);
        if (button) {
            button.disabled = true;
        }
    }
}

// Hide loading spinner
function hideSpinner(spinnerId, buttonId = null) {
    const spinner = document.getElementById(spinnerId);
    if (spinner) {
        spinner.classList.add('d-none');
    }
    
    if (buttonId) {
        const button = document.getElementById(buttonId);
        if (button) {
            button.disabled = false;
        }
    }
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        day: 'numeric',
        month: 'short',
        year: 'numeric'
    });
}

// Format time
function formatTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Clear session data
function clearSessionData() {
    sessionStorage.removeItem('pnr');
    sessionStorage.removeItem('flightId');
    sessionStorage.removeItem('selectedSeat');
}

// Redirect to home page
function goToHome() {
    clearSessionData();
    window.location.href = '/';
}

// Add event listener to logout links
document.addEventListener('DOMContentLoaded', function() {
    const logoutLinks = document.querySelectorAll('.logout-link');
    logoutLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            goToHome();
        });
    });
});