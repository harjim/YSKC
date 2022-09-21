using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class deleteHasAttendanceCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "hasAttendance",
                table: "p_rdEmployee");

            migrationBuilder.AlterColumn<decimal>(
                name: "workHours",
                table: "p_equipment_used",
                type: "decimal(18,2)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "remainHours",
                table: "p_equipment_used",
                type: "decimal(18,2)",
                nullable: false,
                oldClrType: typeof(decimal));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "hasAttendance",
                table: "p_rdEmployee",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AlterColumn<decimal>(
                name: "workHours",
                table: "p_equipment_used",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)");

            migrationBuilder.AlterColumn<decimal>(
                name: "remainHours",
                table: "p_equipment_used",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)");
        }
    }
}
