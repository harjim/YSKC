using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class t_projectInvestMent_1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "InspectionOfReport",
                table: "t_projectInvestMent",
                newName: "inspectionOfReport");

            migrationBuilder.RenameColumn(
                name: "InspectionOfPaid",
                table: "t_projectInvestMent",
                newName: "inspectionOfPaid");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "inspectionOfReport",
                table: "t_projectInvestMent",
                newName: "InspectionOfReport");

            migrationBuilder.RenameColumn(
                name: "inspectionOfPaid",
                table: "t_projectInvestMent",
                newName: "InspectionOfPaid");
        }
    }
}
