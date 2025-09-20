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

  const leftButtonClass = `p-1 ${leftButtonDisabled ? 'text-grey-500 disabled' : 'text-grey-100 cursor'}`;
  const rightButtonClass = `p-1 ${rightButtonDisabled ? 'text-grey-500 disabled' : 'text-grey-100 cursor'}`;

  return (
    <div className="flex justify-start items-center my-4">
        <span className='text-sm text-grey-200 m-r-2'>
            Page {currentPage + 1} of {totalPages}
        </span>

        <span
            title="Previous page"
            onClick={() => onPageChange(currentPage - 1)}
            className={leftButtonClass}
        >
            <ArrowBigLeft size={20} />
        </span>

        <span
            title="Next page"
            onClick={() => onPageChange(currentPage + 1)}
            className={rightButtonClass}
        >
            <ArrowBigRight size={20} />
        </span>
    </div>
  );
};

export default Pagination;