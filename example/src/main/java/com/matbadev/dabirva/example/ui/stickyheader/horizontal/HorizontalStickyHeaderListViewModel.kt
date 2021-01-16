package com.matbadev.dabirva.example.ui.stickyheader.horizontal

import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.decoration.HorizontalStickyHeaderDecoration
import com.matbadev.dabirva.decoration.ItemHeaderProvider
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.data.Note
import com.matbadev.dabirva.example.data.NotePriority
import com.matbadev.dabirva.example.data.NoteRepository
import com.matbadev.dabirva.example.ui.HeaderColumnViewModel
import com.matbadev.dabirva.example.ui.NoteColumnViewModel

class HorizontalStickyHeaderListViewModel(
    private val noteRepository: NoteRepository,
) : BaseScreenViewModel<HorizontalStickyHeaderListEvent, HorizontalStickyHeaderListArguments>() {

    lateinit var dabirvaData: DabirvaData

    override fun initWithArguments(arguments: HorizontalStickyHeaderListArguments?) {
        super.initWithArguments(arguments)
        dabirvaData = DabirvaData(
            items = noteRepository.getNotes()
                .groupBy { note -> note.priority }
                .flatMap { noteEntry -> buildHorizontalGroupViewModels(noteEntry) },
            itemDecorations = listOf(
                HorizontalStickyHeaderDecoration(
                    headerPositionProvider = ItemHeaderProvider { it is HeaderColumnViewModel },
                ),
            ),
        )
    }

    private fun buildHorizontalGroupViewModels(noteEntry: Map.Entry<NotePriority, List<Note>>): Sequence<ItemViewModel> {
        return sequence {
            yield(HeaderColumnViewModel(
                id = noteEntry.key.ordinal.toLong(),
                text = noteEntry.key.name,
            ))
            noteEntry.value.forEach { note: Note ->
                yield(NoteColumnViewModel(
                    id = note.id,
                    text = note.text,
                    color = note.color,
                ))
            }
        }
    }

}