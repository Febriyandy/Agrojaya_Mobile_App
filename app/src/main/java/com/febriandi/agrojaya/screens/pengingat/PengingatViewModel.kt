package com.febriandi.agrojaya.screens.pengingat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.PengingatRepository
import com.febriandi.agrojaya.model.Pengingat
import com.febriandi.agrojaya.screens.pengingat.notifikasiPengingat.jadwalkanNotifikasi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

//viewmodel pengingat
@HiltViewModel
class PengingatViewModel @Inject constructor(
    private val pengingatRepository: PengingatRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _daftarPengingat = MutableStateFlow<List<Pengingat>>(emptyList())
    val daftarPengingat: StateFlow<List<Pengingat>> = _daftarPengingat.asStateFlow()


    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()


    private val _selectedPengingat = MutableStateFlow<Pengingat?>(null)
    val selectedPengingat: StateFlow<Pengingat?> = _selectedPengingat.asStateFlow()


    private val _pengingatSaatIni = MutableStateFlow<Pengingat?>(null)
    val pengingatSaatIni: StateFlow<Pengingat?> = _pengingatSaatIni.asStateFlow()


    init {
        muatDaftarPengingat(LocalDate.now())
        cariPengingatTerdekat()
    }

    fun muatDaftarPengingat(tanggal: LocalDate) {
        viewModelScope.launch {
            _selectedDate.value = tanggal
            pengingatRepository.getDaftarPengingatByTanggal(tanggal)
                .collect { pengingat ->
                    val daftarPengingatTerurut = pengingat.sortedBy { it.jam }
                    _daftarPengingat.value = daftarPengingatTerurut
                }
        }
    }

    fun cariPengingatTerdekat() {
        viewModelScope.launch {
            val sekarang = LocalDateTime.now()
            val daftarPengingat = pengingatRepository.getDaftarPengingatByTanggal(sekarang.toLocalDate()).first()

            val pengingatTerdekat = daftarPengingat
                .filter { pengingat ->
                    val waktuBersih = pengingat.jam.replace(" WIB", "").replace(" WITA", "").replace(" WIT", "")
                    val waktuPengingat = LocalDateTime.of(
                        sekarang.toLocalDate(),
                        LocalTime.parse(waktuBersih)
                    )
                    waktuPengingat >= sekarang
                }
                .minByOrNull { pengingat ->
                    val waktuBersih = pengingat.jam.replace(" WIB", "").replace(" WITA", "").replace(" WIT", "")
                    val waktuPengingat = LocalDateTime.of(
                        sekarang.toLocalDate(),
                        LocalTime.parse(waktuBersih)
                    )
                    Duration.between(sekarang, waktuPengingat)
                }

            _pengingatSaatIni.value = pengingatTerdekat
        }
    }

    fun mulaiPemantauanPengingat() {
        viewModelScope.launch {
            while(true) {
                cariPengingatTerdekat()
                delay(60_000)
            }
        }
    }

    fun tambahPengingat(pengingat: Pengingat) {
        viewModelScope.launch {
            pengingatRepository.tambahPengingat(pengingat)
            jadwalkanNotifikasi(context, pengingat)
        }
    }

    fun getPengingatById(id: Int) {
        viewModelScope.launch {
            val pengingat = pengingatRepository.getPengingatById(id).first()
            _selectedPengingat.value = pengingat
        }
    }

    fun perbaruidPengingat(pengingat: Pengingat) {
        viewModelScope.launch {
            pengingatRepository.perbaruidPengingat(pengingat)
            jadwalkanNotifikasi(context, pengingat)
        }
    }

    fun hapusPengingat(pengingat: Pengingat) {
        viewModelScope.launch {
            pengingatRepository.hapusPengingat(pengingat)
        }
    }
}