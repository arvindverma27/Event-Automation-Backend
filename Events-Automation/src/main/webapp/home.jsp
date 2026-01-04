<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Home Page - Event Automation</title>

<style>
body {
	margin: 0;
	font-family: Arial, sans-serif;
	
	
	
	background: #f5f5f5
}

.header {
	position: fixed;
	top: 0;
	width: 100%;
	background: rgba(0, 0, 0, 0.6);
	padding: 12px 24px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	z-index: 100
}

.logo img {
	height: 40px
}

.nav {
	display: flex;
	gap: 18px
}

.nav a {
	color: #fff;
	text-decoration: none;
	font-size: 15px
}

.btn-group {
	display: flex;
	gap: 12px
}

.btn {
	padding: 8px 14px;
	background: #ffb300;
	border-radius: 6px;
	text-decoration: none;
	color: #000;
	font-weight: 700
}

.hero {
	height: 62vh;
	background: url('/mnt/data/49628b52-1b32-47cc-a7c7-0553b48342d2.png')
		rgba(0, 0, 0, 0.55);
	background-blend-mode: overlay;
	background-size: cover;
	background-position: center;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	text-align: center;
	color: #fff;
	padding: 20px;
	margin-top: 64px
}

.hero h1 {
	font-size: 44px;
	margin: 6px 0
}

.hero p {
	font-size: 16px;
	margin: 0
}

.filters {
	display: flex;
	gap: 10px;
	justify-content: center;
	margin-top: 18px
}

.filter-btn {
	padding: 8px 12px;
	border-radius: 6px;
	border: 1px solid rgba(0, 0, 0, 0.08);
	cursor: pointer;
	background: #fff
}

.filter-btn.active {
	background: #ffb300;
	font-weight: 700
}

.section-title {
	text-align: center;
	margin: 30px 0 10px;
	font-size: 26px
}

.events {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
	gap: 18px;
	padding: 18px 30px 60px
}

.event-card {
	background: #fff;
	padding: 12px;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12)
}

.event-card img {
	width: 100%;
	height: 140px;
	border-radius: 8px;
	object-fit: cover
}

.event-card h3 {
	margin: 10px 0 6px;
	font-size: 18px
}

.event-card p {
	margin: 0;
	color: #333;
	font-size: 14px
}

@media ( max-width :768px) {
	.hero h1 {
		font-size: 28px
	}
	.nav {
		display: none
	}
	.header {
		padding: 10px
	}
}
</style>
</head>
<body>

	<div class="header">
		<div class="logo">
			<img src="/mnt/data/49628b52-1b32-47cc-a7c7-0553b48342d2.png"
				alt="Event-Automation Logo" />
		</div>

		<div class="nav">
			<a href="#">Home</a> <a href="#">Events</a> <a href="#">Contact</a>
		</div>

		<div class="btn-group">
			<a class="btn" href="signupVolunteer.jsp">Volunteer Signup</a> <a
				class="btn" href="signupStudent.jsp">Student Signup</a>
		</div>
	</div>


	<div class="hero">
		<h1>Discover The City Gems</h1>
		<p>Find great places to stay, eat, shop, or visit — and catch live
			events near you.</p>

		<div class="filters" style="margin-top: 18px; flex-wrap: wrap">
			<button class="filter-btn active" data-filter="all">All
				Events</button>
			<button class="filter-btn" data-filter="upcoming">Upcoming</button>
			<button class="filter-btn" data-filter="lastmonth">Last
				Month</button>
			<button class="filter-btn" data-filter="thisweek">This Week</button>

			<input id="keywordInput" placeholder="Search keyword"
				style="padding: 8px 10px; border-radius: 6px; border: 1px solid rgba(0, 0, 0, 0.12); min-width: 180px" />

			<button id="searchBtn" class="filter-btn">Search</button>
		</div>
	</div>

	<h2 class="section-title">Live Events</h2>

	<div id="eventsContainer" class="events">
		<c:if test="${not empty eventsDTOList}">
			<c:forEach var="ev" items="${eventsDTOList}">
				<div class="event-card">
					<img src="https://via.placeholder.com/320x180"
						alt="${ev.eventName}" />
					<h3>${ev.eventName}</h3>
					<p>${ev.location}— ${ev.eventDate}</p>
				</div>
			</c:forEach>
		</c:if>
	</div>

	<script>
function renderEvents(list) {
    const container = document.getElementById('eventsContainer');
    container.innerHTML = '';

    if (!list || list.length === 0) {
        container.innerHTML =
            '<div style="grid-column:1/-1;text-align:center;color:#666;padding:30px;background:#fff;border-radius:8px">No events found.</div>';
        return;
    }

    list.forEach(ev => {
        const card = document.createElement('div');
        card.className = 'event-card';

        const img = document.createElement('img');
        img.src = ev.imageUrl || 'https://via.placeholder.com/320x180';

        const h3 = document.createElement('h3');
        h3.textContent = ev.eventName || 'Untitled Event';

        const p = document.createElement('p');
        p.textContent = (ev.location || 'Unknown') + ' — ' + (ev.eventDate || 'Date N/A');

        card.appendChild(img);
        card.appendChild(h3);
        card.appendChild(p);

        container.appendChild(card);
    });
}

async function loadAll() {
    const res = await fetch('events?action=findAll');
    renderEvents(await res.json());
}

async function loadUpcoming() {
    const today = new Date().toISOString().slice(0, 10);
    const res = await fetch('events?action=searchByDate&start_date=' + today);
    renderEvents(await res.json());
}

async function loadLastMonth() {
    const res = await fetch('events?action=searchByDate&last_month=true');
    renderEvents(await res.json());
}

async function loadThisWeek() {
    const now = new Date();
    const start = new Date(now);
    start.setDate(now.getDate() - now.getDay());
    const end = new Date(start);
    end.setDate(start.getDate() + 6);

    const s = start.toISOString().slice(0, 10);
    const e = end.toISOString().slice(0, 10);

    const res = await fetch(`events?action=searchByDate&start_date=${s}&end_date=${e}`);
    renderEvents(await res.json());
}

async function searchByKeyword(keyword) {
    const res = await fetch('events?action=searchByKeyword&keyword=' + encodeURIComponent(keyword));
    renderEvents(await res.json());
}

document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', async function () {
        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');

        const f = this.getAttribute('data-filter');
        if (f === 'all') loadAll();
        else if (f === 'upcoming') loadUpcoming();
        else if (f === 'lastmonth') loadLastMonth();
        else if (f === 'thisweek') loadThisWeek();
    });
});

document.getElementById('searchBtn').addEventListener('click', function () {
    const kw = document.getElementById('keywordInput').value.trim();
    if (kw) searchByKeyword(kw);
});

(function init() {
    const existing = document.querySelectorAll('#eventsContainer .event-card').length > 0;
    if (!existing) loadAll();
})();
</script>

</body>
</html>
