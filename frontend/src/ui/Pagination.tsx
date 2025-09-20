import { ArrowBigLeft, ArrowBigRight } from 'lucide-react';
import React from 'react';

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
  const leftButtonDisabled = currentPage === 0;
  const rightButtonDisabled = currentPage >= totalPages - 1;

  const leftButtonClass = `p-2 m-r-3 rounded ${leftButtonDisabled ? 'bg-grey-500 text-grey-800 disabled' : 'bg-black text-grey-100 cursor'}`;
  const rightButtonClass = `p-2 m-r-3 rounded ${rightButtonDisabled ? 'bg-grey-500 text-grey-800 disabled' : 'bg-black text-grey-100 cursor'}`;

  return (
    <div className="flex justify-between items-center my-4">

      <div>
        <button
            title="Previous page"
            onClick={() => onPageChange(currentPage - 1)}
            disabled={leftButtonDisabled}
            className={leftButtonClass}
        >
            <ArrowBigLeft size={15} />
        </button>

        <button
            title="Next page"
            onClick={() => onPageChange(currentPage + 1)}
            disabled={rightButtonDisabled}
            className={rightButtonClass}
        >
            <ArrowBigRight size={15} />
        </button>
      </div>
        
      <span className='text-sm text-grey-200'>
        Page {currentPage + 1} of {totalPages}
      </span>
    </div>
  );
};

export default Pagination;