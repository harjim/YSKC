using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addSolution_shareSolutionIdAndTypeAndDataIdUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_solution_share_solutionId_type_dataId",
                table: "solution_share",
                columns: new[] { "solutionId", "type", "dataId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_solution_share_solutionId_type_dataId",
                table: "solution_share");
        }
    }
}
