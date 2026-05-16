import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Vehicule {
  idv?: number;
  immatriculation: string;
  modele: string;
  type: string;
  kilometrage: number;
  statut: string;
}

interface Chauffeur {
  idc?: number;
  nom: string;
  permis: string;
  experience: number;
}

interface Mission {
  idm?: number;
  pointDepart: string;
  destination: string;
  distance: number;
  vehiculeId?: number;
  chauffeurId?: number;
  vehiculeImmatriculation?: string;
  chauffeurNom?: string;
}

interface Consommation {
  idco?: number;
  dateConsommation: string;
  quantiteCarburant: number;
  coutTotal: number;
  vehiculeId?: number;
  vehiculeImmatriculation?: string;
}

interface Dashboard {
  vehiculesDisponibles: number;
  vehiculesEnPanne: number;
  coutCarburantTotal: number;
}

interface FuelCost {
  vehiculeId: number;
  immatriculation: string;
  modele: string;
  coutTotal: number;
}

interface FleetUsage {
  vehiculeId: number;
  immatriculation: string;
  modele: string;
  nombreMissions: number;
}

interface RuntimeConfig {
  apiUrl?: string;
}

type Tab = 'dashboard' | 'vehicules' | 'chauffeurs' | 'missions' | 'consommations';

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private apiUrl = 'http://localhost:8081';

  activeTab: Tab = 'dashboard';
  loading = false;
  message = '';
  error = '';

  vehicules: Vehicule[] = [];
  chauffeurs: Chauffeur[] = [];
  missions: Mission[] = [];
  consommations: Consommation[] = [];
  maintenanceVehicules: Vehicule[] = [];
  fuelCosts: FuelCost[] = [];
  fleetUsage: FleetUsage[] = [];
  dashboard: Dashboard = {
    vehiculesDisponibles: 0,
    vehiculesEnPanne: 0,
    coutCarburantTotal: 0
  };

  vehiculeForm: Vehicule = this.emptyVehicule();
  chauffeurForm: Chauffeur = this.emptyChauffeur();
  missionForm: Mission = this.emptyMission();
  consommationForm: Consommation = this.emptyConsommation();

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRuntimeConfig();
  }

  loadRuntimeConfig(): void {
    this.http.get<RuntimeConfig>('/config.json').subscribe({
      next: config => {
        if (config.apiUrl) {
          this.apiUrl = config.apiUrl;
        }
        this.refreshAll();
      },
      error: () => this.refreshAll()
    });
  }

  setTab(tab: Tab): void {
    this.activeTab = tab;
    this.message = '';
    this.error = '';
  }

  refreshAll(): void {
    this.loading = true;
    this.error = '';

    this.loadVehicules();
    this.loadChauffeurs();
    this.loadMissions();
    this.loadConsommations();
    this.loadDashboard();

    this.loading = false;
  }

  loadVehicules(): void {
    this.http.get<Vehicule[]>(`${this.apiUrl}/vehicules/getAll`).subscribe({
      next: data => this.vehicules = data,
      error: () => this.showError('Impossible de charger les vehicules')
    });

    this.http.get<Vehicule[]>(`${this.apiUrl}/vehicules/maintenance`).subscribe({
      next: data => this.maintenanceVehicules = data,
      error: () => this.showError('Impossible de charger la maintenance')
    });
  }

  loadChauffeurs(): void {
    this.http.get<Chauffeur[]>(`${this.apiUrl}/chauffeurs/getAll`).subscribe({
      next: data => this.chauffeurs = data,
      error: () => this.showError('Impossible de charger les chauffeurs')
    });
  }

  loadMissions(): void {
    this.http.get<Mission[]>(`${this.apiUrl}/missions/getAll`).subscribe({
      next: data => this.missions = data,
      error: () => this.showError('Impossible de charger les missions')
    });
  }

  loadConsommations(): void {
    this.http.get<Consommation[]>(`${this.apiUrl}/consommations/getAll`).subscribe({
      next: data => this.consommations = data,
      error: () => this.showError('Impossible de charger les consommations')
    });
  }

  loadDashboard(): void {
    this.http.get<Dashboard>(`${this.apiUrl}/dashboard/summary`).subscribe({
      next: data => this.dashboard = data,
      error: () => this.showError('Impossible de charger le resume')
    });

    this.http.get<FuelCost[]>(`${this.apiUrl}/dashboard/fuel-costs`).subscribe({
      next: data => this.fuelCosts = data,
      error: () => this.showError('Impossible de charger les couts carburant')
    });

    this.http.get<FleetUsage[]>(`${this.apiUrl}/dashboard/fleet-usage`).subscribe({
      next: data => this.fleetUsage = data,
      error: () => this.showError("Impossible de charger l'utilisation de la flotte")
    });
  }

  saveVehicule(): void {
    const request = this.vehiculeForm.idv
      ? this.http.put(`${this.apiUrl}/vehicules/update/${this.vehiculeForm.idv}`, this.vehiculeForm, { responseType: 'text' })
      : this.http.post(`${this.apiUrl}/vehicules/add`, this.vehiculeForm, { responseType: 'text' });

    request.subscribe({
      next: () => {
        this.vehiculeForm = this.emptyVehicule();
        this.showMessage('Vehicule enregistre');
        this.loadVehicules();
        this.loadDashboard();
      },
      error: () => this.showError("Erreur lors de l'enregistrement du vehicule")
    });
  }

  editVehicule(vehicule: Vehicule): void {
    this.vehiculeForm = { ...vehicule };
  }

  deleteVehicule(id?: number): void {
    if (!id) return;

    this.http.delete(`${this.apiUrl}/vehicules/delete/${id}`).subscribe({
      next: () => {
        this.showMessage('Vehicule supprime');
        this.loadVehicules();
        this.loadDashboard();
      },
      error: () => this.showError('Impossible de supprimer le vehicule')
    });
  }

  saveChauffeur(): void {
    const request = this.chauffeurForm.idc
      ? this.http.put(`${this.apiUrl}/chauffeurs/update/${this.chauffeurForm.idc}`, this.chauffeurForm, { responseType: 'text' })
      : this.http.post(`${this.apiUrl}/chauffeurs/add`, this.chauffeurForm, { responseType: 'text' });

    request.subscribe({
      next: () => {
        this.chauffeurForm = this.emptyChauffeur();
        this.showMessage('Chauffeur enregistre');
        this.loadChauffeurs();
      },
      error: () => this.showError("Erreur lors de l'enregistrement du chauffeur")
    });
  }

  editChauffeur(chauffeur: Chauffeur): void {
    this.chauffeurForm = { ...chauffeur };
  }

  deleteChauffeur(id?: number): void {
    if (!id) return;

    this.http.delete(`${this.apiUrl}/chauffeurs/delete/${id}`).subscribe({
      next: () => {
        this.showMessage('Chauffeur supprime');
        this.loadChauffeurs();
      },
      error: () => this.showError('Impossible de supprimer le chauffeur')
    });
  }

  saveMission(): void {
    const request = this.missionForm.idm
      ? this.http.put(`${this.apiUrl}/missions/update/${this.missionForm.idm}`, this.missionForm, { responseType: 'text' })
      : this.http.post(`${this.apiUrl}/missions/add`, this.missionForm, { responseType: 'text' });

    request.subscribe({
      next: () => {
        this.missionForm = this.emptyMission();
        this.showMessage('Mission enregistree');
        this.loadMissions();
        this.loadDashboard();
      },
      error: () => this.showError("Erreur lors de l'enregistrement de la mission")
    });
  }

  editMission(mission: Mission): void {
    this.missionForm = { ...mission };
  }

  deleteMission(id?: number): void {
    if (!id) return;

    this.http.delete(`${this.apiUrl}/missions/delete/${id}`).subscribe({
      next: () => {
        this.showMessage('Mission supprimee');
        this.loadMissions();
        this.loadDashboard();
      },
      error: () => this.showError('Impossible de supprimer la mission')
    });
  }

  saveConsommation(): void {
    const request = this.consommationForm.idco
      ? this.http.put(`${this.apiUrl}/consommations/update/${this.consommationForm.idco}`, this.toConsommationPayload(), { responseType: 'text' })
      : this.http.post(`${this.apiUrl}/consommations/add`, this.toConsommationPayload(), { responseType: 'text' });

    request.subscribe({
      next: () => {
        this.consommationForm = this.emptyConsommation();
        this.showMessage('Consommation enregistree');
        this.loadConsommations();
        this.loadDashboard();
      },
      error: () => this.showError("Erreur lors de l'enregistrement de la consommation")
    });
  }

  editConsommation(consommation: Consommation): void {
    this.consommationForm = {
      ...consommation,
      dateConsommation: this.toInputDate(consommation.dateConsommation)
    };
  }

  deleteConsommation(id?: number): void {
    if (!id) return;

    this.http.delete(`${this.apiUrl}/consommations/delete/${id}`).subscribe({
      next: () => {
        this.showMessage('Consommation supprimee');
        this.loadConsommations();
        this.loadDashboard();
      },
      error: () => this.showError('Impossible de supprimer la consommation')
    });
  }

  resetForms(): void {
    this.vehiculeForm = this.emptyVehicule();
    this.chauffeurForm = this.emptyChauffeur();
    this.missionForm = this.emptyMission();
    this.consommationForm = this.emptyConsommation();
  }

  formatDate(value: string): string {
    return value ? new Date(value).toLocaleDateString('fr-FR') : '';
  }

  private toConsommationPayload(): Consommation {
    return {
      ...this.consommationForm,
      dateConsommation: `${this.consommationForm.dateConsommation}T00:00:00.000+00:00`
    };
  }

  private toInputDate(value: string): string {
    if (!value) return '';
    return new Date(value).toISOString().slice(0, 10);
  }

  private showMessage(message: string): void {
    this.message = message;
    this.error = '';
  }

  private showError(error: string): void {
    this.error = error;
  }

  emptyVehicule(): Vehicule {
    return {
      immatriculation: '',
      modele: '',
      type: 'UTILITAIRE',
      kilometrage: 0,
      statut: 'DISPONIBLE'
    };
  }

  emptyChauffeur(): Chauffeur {
    return {
      nom: '',
      permis: 'B',
      experience: 0
    };
  }

  emptyMission(): Mission {
    return {
      pointDepart: '',
      destination: '',
      distance: 0,
      vehiculeId: undefined,
      chauffeurId: undefined
    };
  }

  emptyConsommation(): Consommation {
    return {
      dateConsommation: new Date().toISOString().slice(0, 10),
      quantiteCarburant: 0,
      coutTotal: 0,
      vehiculeId: undefined
    };
  }
}
