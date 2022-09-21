using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_info_summaryDataentryToAuxiliary : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "materialdataentry",
                table: "p_info_summary",
                newName: "materialAuxiliary");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "materialAuxiliary",
                table: "p_info_summary",
                newName: "materialdataentry");
        }
    }
}
