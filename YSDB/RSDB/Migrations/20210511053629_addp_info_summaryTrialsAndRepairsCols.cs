using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addp_info_summaryTrialsAndRepairsCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "repairAuxiliary",
                table: "p_info_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "repairRaw",
                table: "p_info_summary",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "repairReserve",
                table: "p_info_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "trialAuxiliary",
                table: "p_info_summary",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "trialRaw",
                table: "p_info_summary",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "trialReserve",
                table: "p_info_summary",
                type: "decimal(18,2)",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "repairAuxiliary",
                table: "p_info_summary");

            migrationBuilder.DropColumn(
                name: "repairRaw",
                table: "p_info_summary");

            migrationBuilder.DropColumn(
                name: "repairReserve",
                table: "p_info_summary");

            migrationBuilder.DropColumn(
                name: "trialAuxiliary",
                table: "p_info_summary");

            migrationBuilder.DropColumn(
                name: "trialRaw",
                table: "p_info_summary");

            migrationBuilder.DropColumn(
                name: "trialReserve",
                table: "p_info_summary");
        }
    }
}
