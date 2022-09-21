using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterC_rd_summaryColHighTechRatioToHighTechIncome : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "highTechRatio",
                table: "c_rd_summary",
                newName: "highTechIncome");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "highTechIncome",
                table: "c_rd_summary",
                newName: "highTechRatio");
        }
    }
}
